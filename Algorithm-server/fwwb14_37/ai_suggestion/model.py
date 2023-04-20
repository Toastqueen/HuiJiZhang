import torch
import torch.nn as nn

class ResLin(nn.Module):
    def __init__(self, num_input, dropout=0.2):
        super().__init__()
        self.fc1 = nn.Linear(num_input, 128)
        self.ac = nn.ReLU()
        self.dropout = nn.Dropout(dropout)
        self.fc2 = nn.Linear(128, num_input)

    def forward(self, X):
        X1 = self.fc2(self.ac(self.fc1(X)))
        X = self.ac(self.dropout(X1) + X)
        return X
    

class SELin(nn.Module):
    def __init__(self, num_input, dropout=0.2):
        super().__init__()
        self.fc1 = nn.Linear(num_input, 256)
        self.ac = nn.ReLU()
        self.fc2 = nn.Linear(256, num_input)
        self.dropout = nn.Dropout(dropout)
        self.softmax = nn.Softmax(dim=1)

    def forward(self, X):
        X1 = self.dropout(self.fc2(self.ac(self.fc1(X))))
        weight = self.softmax(X1)
        return torch.mul(X, weight)


class Model(nn.Module):
    def __init__(self, num_input, num_label, dropout=0.2):
        super().__init__()
        self.start = nn.Linear(num_input, 64)
        self.reslin = ResLin(64, dropout=0.2)
        self.selin = SELin(64, dropout=0.2)
        self.classifier = nn.Sequential(
            nn.Linear(64, 256), nn.ReLU(), nn.Dropout(dropout), 
            nn.Linear(256, num_label*3)
        )
        
        self.num_label = num_label

    def forward(self, X):
        X = self.start(X)
        X = self.reslin(X)
        X = self.selin(X)
        X = self.classifier(X).reshape(X.shape[0], self.num_label, 3)
        return X
    

def getNet():
    net = Model(5, 5, dropout=0.2)

    def init_weights(m):
        if type(m) == nn.Linear or type(m) == nn.Conv2d :
            nn.init.xavier_uniform_(m.weight)
    net.apply(init_weights)

    return net