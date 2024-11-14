from configs import Database

import functions.functionManager as fn
from flask import Flask, request
# from flask_cors import CORS
import json

app = Flask(__name__)
# CORS(app)


def getDefaultHeaders():
    return {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Headers': '*'
    }   


@app.route('/ping', methods=['GET'])
def ping():

    headers = getDefaultHeaders()

    result, response = fn.ping()

    if(result == 200):
        return { 'response': response }, 200, headers 
    else:
        return { 'response': response }, 500, headers 


@app.route('/evento', methods=['GET', 'OPTIONS'])
def evento():
    
    headers = getDefaultHeaders()

    # preflight
    if request.method == 'OPTIONS':
        return {}, 204, headers
    
    try:
        id = int(request.args.get('id'))

        if(id is None):
            raise

    except:
        return { 'response': 'ERRO: Parametros mal configurados' }, 400, headers 


    status, response = fn.evento(Database, id)

    if(status == 200):
        return { 'response': response }, 200, headers
    if(status == 404):
        return { 'response': response }, 404, headers 
    else:
        return { 'response': response }, 500, headers 


if __name__ == "__main__":
    app.run(debug=True)