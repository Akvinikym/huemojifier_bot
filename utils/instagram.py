import requests
import json
import http


def download_instagram_media(media_link):
    with open('instagram.txt', 'r') as file:
        token = file.read().rstrip()

    retries = 0
    while retries < 10:
        # download
        conn = http.client.HTTPSConnection(
            "tiktok-video-no-watermark2.p.rapidapi.com")
        headers = {
            'X-RapidAPI-Key': token,
            'X-RapidAPI-Host': "instagram-downloader-download-instagram-videos-stories.p.rapidapi.com"
        }
        conn.request("GET", f'/index?url={media_link}', headers=headers)
        response = conn.getresponse()
        if response.status != 200:
            retries = retries + 1
            continue
        response_json = json.loads(response.read())

        is_image = response_json['Type'].find('Image') != -1
        link = response_json['media']
        with requests.get(link) as r:
            return is_image, r.content

    raise Exception(response.read())
