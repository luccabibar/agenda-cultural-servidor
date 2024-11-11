from flask import Flask, request
# from flask_cors import CORS
import json

app = Flask(__name__)
# CORS(app)


@app.route('/ping', methods=['GET'])
def ping():

    headers = {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
    }

    return { 'response': 'pong' }, 200, headers 


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