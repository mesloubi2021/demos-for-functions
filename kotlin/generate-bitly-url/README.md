# 📧  Shorten URL using Bitly API
This function takes an url as an input, generate a short URL using Bitly and output the shorten URL.

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **BITLY_ACCESS_TOKEN** - Your Bitly access token

Your data must include the following fields:

```json
{
    "url": "https://appwrite.io"
}
```

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```

$ tar -zcvf code.tar.gz .
```

