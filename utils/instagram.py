import requests
import json
import http


def download_instagram_media(media_link):
    with open('instagram.txt', 'r') as file:
        token = file.read().rstrip()

    # download
    conn = http.client.HTTPSConnection(
        "tiktok-video-no-watermark2.p.rapidapi.com")
    headers = {
        'X-RapidAPI-Key': token,
        'X-RapidAPI-Host': "instagram-downloader-download-instagram-videos-stories.p.rapidapi.com"
    }
    conn.request("GET", f'/index?url={media_link}', headers=headers)
    response_json = json.loads(conn.getresponse().read())

    is_image = response_json['Type'].find('Image') != -1
    link = response_json['media']
    with requests.get(link) as r:
        return is_image, r.content
