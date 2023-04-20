import requests
import json

def curlOpenAI(aiDto):

    url = "https://openai.api2d.net/v1/chat/completions"

    headers = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer fkxxx' # <-- 把 fkxxxxx 替换成你自己的 Forward Key，注意前面的 Bearer 要保留，并且和 Key 中间有一个空格。
    }

    qustion = "你是我的机器人财务助理，你的名字叫慧记账，我要你为我分析我的消费行为，在你的回答中要表现你是我的财务助手。下面我将提供我的消费行为对象，其中，age代表我的年龄(若为0则不管)，monthBudget代表月预算，yearBudget代表年预算，monthMoneyRecord代表月消费和收入记录，yearMoneyRecord代表年消费和收入记录。（其中如果有餐饮，餐饮是指的一日三餐的费用，不论是在家里还是在外面吃饭）" \
              "在此基础上分析我的预算和消费收入记录,参考大部分人在我这个年龄的情况，最后给出消费分析、总结和建议(可以适当地列出我的数据)。消费建议需要具体,回答总字数不少于300，重点是有年龄一定要结合年龄，而且回答中不要出现如：根据你提供的对象或者年龄这种字样，把你自己当成财务助理。我的行为对象为："+json.dumps(aiDto)
    data = {
      #   gpt-4 gpt-3.5-turbo
      "model": "gpt-3.5-turbo",
      "max_tokens": 1000,
      "messages": [{"role": "user", "content": qustion}]
    }

    response = requests.post(url, headers=headers, json=data)

    print("Status Code", response.status_code)
    print("JSON Response ", response.json())
    return response.json()

if __name__ == '__main__':
    curlOpenAI(1)
