# 🗂 File Backup using the Dropbox API
A sample .NET Cloud Function that leverages Dropbox to create backups of important files uploaded to Appwrite.

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **AppwriteEndpoint** - Your Appwrite Endpoint
* **AppwriteProjectId** - Your Project ID
* **AppwriteAPIKey** - Your Appwrite API key with `files.read` and `files.write` permissions
* **FileId** - Your File ID
* **DropboxAPIKey** - OAuth token from [Dropbox](https://blogs.dropbox.com/developers/2014/05/generate-an-access-token-for-your-own-account) 

## 🚀 Building and Packaging
To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/dotnet/file-backup

$ dotnet publish --runtime linux-x64 --framework net5.0 --no-self-contained
```

* Ensure that your output looks like this 
```
  FileBackup -> ......\demos-for-functions\dotnet\file-backup\bin\Debug\net5.0\linux-x64\FileBackup.dll
  FileBackup -> ......\demos-for-functions\dotnet\file-backup\bin\Debug\net5.0\linux-x64\publish\
```

* Create a tarfile

```bash
$ tar -C bin/Debug/net5.0/linux-x64 -zcvf file-backup.tar.gz publish
```

## ⚡ Execution
* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `dotnet FileBackup.dll`) as your entrypoint command
* Upload your tarfile 
* Click 'Activate'

* Manually run:
- `cd demos-for-functions/dotnet/file-backup`
- `cp appsettings.json.backup appsettings.json`
- Modify config in appsettings.json
- `dotnet build`
- `dotnet run`