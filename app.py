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
    
    # valida parametros
    try:
        id = request.args.get('id', type = int)

        if(id is None):
            raise

    except:
        return { 'response': 'ERRO: Parametros mal configurados' }, 400, headers 


    # executa funcao
    status, response = fn.evento(Database, id)

    # responde
    if(status == 200):
        return { 'response': response }, 200, headers
    elif(status == 404):
        return { 'response': response }, 404, headers 
    else:
        return { 'response': response }, 500, headers 


@app.route('/buscarEventos', methods=['GET', 'OPTIONS'])
def buscarEventos():
    
    headers = getDefaultHeaders()

    # preflight
    if request.method == 'OPTIONS':
        return {}, 204, headers
    
    # valida parametros
    try:
        texto = request.args.get('texto', type = str)
        categoria = request.args.get('categoria', type = str)
        diaUpper = request.args.get('diaUpper', type = str)
        diaLower = request.args.get('diaLower', type = str)
        horaUpper = request.args.get('horaUpper', type = str)
        horaLower = request.args.get('horaLower', type = str)
        regiao = request.args.get('regiao', type = str)

        if(id is None):
            raise

    except:
        return { 'response': 'ERRO: Parametros mal configurados' }, 400, headers 

    # executa funcao
    status, response = fn.buscarEventos(Database, texto, categoria, diaUpper, diaLower, horaUpper, horaLower, regiao)

    # responde
    if(status == 200):
        return { 'response': response }, 200, headers
    elif(status == 404):
        return { 'response': response }, 404, headers 
    else:
        return { 'response': response }, 500, headers
    

if __name__ == "__main__":
    app.run(debug=True)