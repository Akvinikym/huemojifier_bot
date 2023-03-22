import requests
import json
import http


def download_instagram_video(video_link):
    with open('instagram.txt', 'r') as file:
        token = file.read().rstrip()

    # download
    conn = http.client.HTTPSConnection(
        "tiktok-video-no-watermark2.p.rapidapi.com")
    headers = {
        'X-RapidAPI-Key': token,
        'X-RapidAPI-Host': "instagram-downloader-download-instagram-videos-stories.p.rapidapi.com"
    }
    conn.request("GET", f'/index?url={video_link}', headers=headers)
    link = json.loads(conn.getresponse().read())['media']
    with requests.get(link) as r:
        return r.content
