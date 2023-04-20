from sanic import Sanic, response,request
from sanic.response import json

import sys
import json as std_json
import traceback

from deploy import deploy_characteristic

sys.path.append("C:\fwwb14_37")

app = Sanic("GetPhotoInfo")

@app.get("/")
async def index(request):
    return response.text("Hello, Sanic!")


@app.post('/getUserTab')
async def getTab(request):
    print("test")
    try:
        json_data = request.json
        income = float(json_data.get('income'))  # convert income to float
        expenditure = float(json_data.get('expenditure'))  # convert expenditure to float
        diet = float(json_data.get('diet'))  # convert diet to float
        relaxation = float(json_data.get('relaxation'))  # convert relaxation to float
        education = float(json_data.get('education'))  # convert education to float
        shopping = float(json_data.get('shopping'))  # convert shopping to float
        health = float(json_data.get('health'))  # convert health to float
        data = {
            'income': income,
            'expenditure': expenditure,
            'diet': diet,
            'relaxation': relaxation,
            'shopping': shopping,
            'education': education,
            'health': health
        }
        print(data)
        Tab = deploy_characteristic(std_json.dumps(data))
        print(Tab)
        return json({"code":200,"info":Tab})
    except Exception as e:
        traceback.print_exc()
        return json({"code":500,"info":None})

if __name__ == '__main__':
    app.run(host="0.0.0.0",port=8003)