using System;
using System.IO;
using System.Text;
using System.Data;
using Appwrite;
using Dropbox.Api;
using Dropbox.Api.Files;
using System.Text.Json;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

using Microsoft.Extensions.Configuration;

namespace file_backup
{
    class Program
    {
        public static IConfigurationRoot configuration;

        public static async Task Main(string[] args)
        {
            AppConfig config = GetAppConfig();
            
            Client client = new Client();
            client
              .SetEndPoint(config.AppwriteEndpoint)
              .SetProject(config.AppwriteProjectId)
              .SetKey(config.AppwriteAPIKey)
            ;

            Storage storage = new Storage(client);
            File? fileDescription = null;

            try {
                var request = await storage.GetFile(config.FileId);
                var response = await request.Content.ReadAsStringAsync();
                Console.WriteLine(response);
                fileDescription = JsonSerializer.Deserialize<File>(response);
            } catch (AppwriteException e) {
                Console.WriteLine("Failed to call GetFile: " + e.Message);
            }

            string fileContent = null;
            try {
                fileContent = storage.GetFileDownload(config.FileId);
                Console.WriteLine(fileContent);
                // TODO Call API
            } catch (AppwriteException e) {
                Console.WriteLine("Failed to call GetFilePreview: " + e.Message);
            }

            // TODO Call Upload
            // using (var dbx = new DropboxClient(Environment.GetEnvironmentVariable["DROPBOX_API_KEY"]))
            // {
            //     await Upload(dbx, $"/{fileDescription.Name}", fileDescription.Name, fileContent);
            // }
        }

        private static async Task Upload(DropboxClient dbx, string folder, string file, string content)
        {
            using (var mem = new MemoryStream(Encoding.UTF8.GetBytes(content)))
            {
                var updated = await dbx.Files.UploadAsync(
                    folder + "/" + file,
                    WriteMode.Overwrite.Instance,
                    body: mem);
                Console.WriteLine("Saved {0}/{1} rev {2}", folder, file, updated.Rev);
            }
        }

        private static AppConfig GetAppConfig()
        {
            var builder = new ConfigurationBuilder()
                .AddJsonFile($"appsettings.json", true, true)
                .AddEnvironmentVariables();
            var configurationRoot = builder.Build();
            return configurationRoot.GetSection(nameof(AppConfig)).Get<AppConfig>();
        }
    }
}

public class File
{
    [JsonPropertyNameAttribute("$id")]
    public string Id { get; set; }

    [JsonPropertyNameAttribute("name")]
    public string Name { get; set; }

    [JsonPropertyNameAttribute("dateCreated")]
    public int DateCreated { get; set; }
}

public class AppConfig
{
    public string AppwriteEndpoint { get; set; }
	public string AppwriteProjectId { get; set; }
    public string AppwriteAPIKey { get; set; }
	public string FileId { get; set; }
    public string DropboxAPIKey { get; set; }
}
