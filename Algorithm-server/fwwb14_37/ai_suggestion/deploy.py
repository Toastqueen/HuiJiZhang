import sys
sys.path.append("E:\IDEA_workspace\服创-python")
from ai_suggestion.model import getNet
import torch
import torch.nn as nn
import json


net = getNet()
net.load_state_dict(torch.load(r'E:\IDEA_workspace\服创-python\fwwb14_37\ai_suggestion\checkpoint.pth'))
net.eval()


def deploy_characteristic(data):
    data = json.loads(data)
    income, expenditure = data['income'], data['expenditure']
    input = [data['diet'], data['relaxation'], data['shopping'], data['education'], data['health']]
    sum = 0
    for i in input:
        sum += i
    input = torch.tensor(input, dtype=torch.float32)
    input /= sum
    output = net(input.unsqueeze(dim=0)).squeeze(dim=0)
    output_degree = nn.Softmax(dim=1)(output).max(dim=1)
    for i in range(len(output_degree.indices)):
        if output_degree.values[i] < 0.8:
            output_degree.indices[i] = -1

    characteristic = []
    if output_degree.indices[0] == 2:
        characteristic.append((1, output_degree.values[0]))
    if output_degree.indices[1] == 2:
        characteristic.append((2, output_degree.values[1]))
    if output_degree.indices[2] == 2:
        characteristic.append((3, output_degree.values[2]))
    if output_degree.indices[3] == 2:
        characteristic.append((4, output_degree.values[3]))

    if characteristic != []:
        characteristic.sort(key=lambda x: x[1], reverse=True)
        res = characteristic[0][0]
    elif expenditure > 1 * income:
        res = 6
    elif output_degree.indices[4] == 1:
        res = 4
    else:
        res = 7

    return res


if __name__ == '__main__':
    data = {
        'income': 2500,
        'expenditure': 1709,
        'diet': 1100,
        'relaxation': 500,
        'shopping': 200,
        'education': 0,
        'health': 0,
    }
    data = json.dumps(data)
    a = deploy_characteristic(data)
    print(a)