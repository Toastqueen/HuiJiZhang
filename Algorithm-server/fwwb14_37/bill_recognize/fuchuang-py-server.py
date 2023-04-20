from sanic import Sanic, response,request
from ai_suggestion.deploy import deploy_characteristic
from bill_recognize.OpenAI import curlOpenAI
from main import main
from sanic.response import json
import asyncio
import sys
import json as std_json
import traceback


app = Sanic("GetPhotoInfo")
@app.post('/getPhotoJson')
async def getPhotoJson(request):
    try:
        url = request.json["url"]
        print(url)
        task = asyncio.create_task(main(url),True)
        print("异步程序开始 线程处理")
        await task
        info = task.result()
        print("异步程序结束 线程处理")

        return json({"code":200,"info":info})
    except Exception as e:
        print(e)
        return json({"code":500,"info":None})

@app.post('/getOpenAIJson')
async def getOpenAIJson(request):
    try:
        aiDto = request.json["AiDto"]
        print(aiDto)
        info = curlOpenAI(aiDto)
        return json({"code":200,"info":info})
    except Exception as e:
        print(e)
        return json({"code":500,"info":None})

@app.post('/getUserTab')
async def getTab(request):
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
    app.run(host="127.0.0.1",port=8003)