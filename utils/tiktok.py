import requests
import json
import os
import time
import http


def download_tiktok_video(video_link):
    with open('tiktok.txt', 'r') as file:
        token = file.read().rstrip()

    # download
    conn = http.client.HTTPSConnection(
        "tiktok-video-no-watermark2.p.rapidapi.com")
    headers = {
        'X-RapidAPI-Key': token,
        'X-RapidAPI-Host': "tiktok-video-no-watermark2.p.rapidapi.com"
    }
    conn.request("GET", f'/?url={video_link}', headers=headers)
    link = json.loads(conn.getresponse().read())['data']['play']

    # save
    directory = str(round(time.time()))
    filename = str(int(time.time())) + '.mp4'
    path = f'{directory}/{filename}'
    try:
        os.mkdir(directory)
    except:
        pass
    with requests.get(link) as r:
        return r.content
