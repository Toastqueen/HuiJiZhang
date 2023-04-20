import requests

def get_file_content(filePath):
    with open(filePath, 'rb') as fp:
        return fp.read()

class CommonOcr(object):
    def __init__(self, img_url):
        # 请登录后前往 “工作台-账号设置-开发者信息” 查看 x-ti-app-id
        # 示例代码中 x-ti-app-id 非真实数据
        self._app_id = 'aebad4ee760ebe21b3e9a27cf075e03f'
        # 请登录后前往 “工作台-账号设置-开发者信息” 查看 x-ti-secret-code
        # 示例代码中 x-ti-secret-code 非真实数据
        self._secret_code = '7794717dc66877766276eff7d9e2edd0'
        self._img_url = img_url

    def crop_enhance(self):
        # 图片切边增强
        url = 'https://api.textin.com/ai/service/v1/crop_enhance_image'
        head = {}
        params = {
            'enhance_mode': 2
        }
        try:
            #image = get_file_content(self._img_url)
            image = requests.get(self._img_url).content
            head['x-ti-app-id'] = self._app_id
            head['x-ti-secret-code'] = self._secret_code
            result = requests.post(url, data=image, params=params, headers=head)
            return result.text
        except Exception as e:
            print('图片增强出错', e)

    def dewarp(self):
        # 图片切边矫正
        url = 'https://api.textin.com/ai/service/v1/dewarp'
        head = {}
        
        try:
            #image = get_file_content(self._img_url)
            image = requests.get(self._img_url).content
            head['x-ti-app-id'] = self._app_id
            head['x-ti-secret-code'] = self._secret_code
            result = requests.post(url, data=image, headers=head)
            return result.text
        except Exception as e:
            print('图片切边矫正出错', e)


    def recognize(self, image):
        # 国内通用票据识别
        url = 'https://api.textin.com/robot/v1.0/api/bills_crop'
        head = {}
        try:
            head['x-ti-app-id'] = self._app_id
            head['x-ti-secret-code'] = self._secret_code
            result = requests.post(url, data=image, headers=head)
            return result.text
        except Exception as e:
            print('票据识别出错', e)

    def general_ocr(self, image):
        # 通用文字识别
        url = 'https://api.textin.com/ai/service/v2/recognize'
        head = {}
        try:
            head['x-ti-app-id'] = self._app_id
            head['x-ti-secret-code'] = self._secret_code
            result = requests.post(url, data=image, headers=head)
            return result.text
        except Exception as e:
            print('通用文字识别出错', e)
