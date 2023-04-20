import json
import base64
import jizhang
from textinapi import CommonOcr
import os
async def main(img_path, use_recognize_api=False):
    try:
        response = CommonOcr(img_path)
        #result = response.crop_enhance()
        result = response.dewarp()

        result = json.loads(result)
        #pic_bs64 = result['result']['image_list'][0]['image']
        pic_bs64 = result['result']['image']
        pic = base64.b64decode(pic_bs64)

        jzdict = {}
        result = response.general_ocr(pic)
        result = json.loads(result)
        lines = result['result']['lines']
        contents = []
        for line in lines:
            contents.append(line['text'])
        all_words = ' '.join(contents)
        jzdict['all'] = all_words

        if use_recognize_api:
            result = response.recognize(pic)
            result = json.loads(result)

            bills = result['result']['object_list']
            for bill in bills:
                jzdict.update(jizhang.jizhang_use_api(bill))
                break  # 官方api允许一张图多个票据，这里只提供识别出的第一张票据，如果要扩展后面再改

            jzdict.update(jizhang.jizhang_without_api(jzdict['all']))

        else:
            jzdict.update(jizhang.jizhang_without_api(jzdict['all']))

        return json.dumps(jzdict)

    except Exception as e:
        import traceback
        traceback.print_exc()
        jzdict = {
            'superclass': None,
            'subclass': None,
            'money': None,
            'shop': None,
            'detailed': None,
            'all': None
        }
        return json.dumps(jzdict)


if __name__ == '__main__':
    img_url = ''
    res = main(img_url, use_recognize_api=False)