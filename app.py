import functions.functionManager as fn
from flask import Flask, request
# from flask_cors import CORS
import json

app = Flask(__name__)
# CORS(app)


def getDefaultHeaders():
    return {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
    }   


@app.route('/ping', methods=['GET'])
def ping():

    headers = getDefaultHeaders()
    result, response = fn.ping()

    if(result):
        return { 'response': response }, 200, headers 
    else:
        return { 'response': response }, 500, headers 


@app.route('/event', methods=['GET'])
def event():

    headers = getDefaultHeaders()

    id = request.args.get('id')

    if(id == None):
        return { 'response': 'ERRO: Parametros mal configurados' }, 400, headers 

    result, response = fn.evento(id)

    if(result):
        return { 'response': response }, 200, headers 
    else:
        return { 'response': response }, 500, headers 


@app.route('/testfile', methods=['GET'])
def read():
    

    # if request.method == 'OPTIONS':
    #     print('pref')
    #     return 204, headers

    # dados = json.loads(request.data)

    # print(prbl)

    msg = ""

    try:
        f = open("data/testfile.txt", "r")
        msg = f.read()

        f.close()

    except IOError as ex:
        msg = "ERRO: Nada para ler ainda!"

    headers = {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
    }

    print(msg)

    return { 'response': msg }, 200, headers


@app.route('/testfile', methods=['POST'])
def write():
    
    # if request.method == 'OPTIONS':
    #     print('pref')
    #     return 204, headers

    msg = request.form.get('msg')

    resp = ""

    try:
        f = open("data/testfile.txt", "w")
        f.write(msg)

        f.close()

        resp = "Escrita com sucesso!"

    except IOError as ex:
        resp = "ERRO: Impossivel escrever!"

    headers = {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
    }

    return resp, 200, headers


if __name__ == "__main__":
    app.run(debug=True)