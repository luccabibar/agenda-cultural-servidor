from configs import *
from functions.common.customExceptions import *

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

# = = = = = = = = = = = = = =
# Ping
# = = = = = = = = = = = = = =

@app.route('/ping', methods=['GET'])
def ping():

    headers = getDefaultHeaders()

    result, response = fn.ping()

    if(result == 200):
        return { 'response': response }, 200, headers 
    else:
        return { 'response': response }, 500, headers 

# = = = = = = = = = = = = = =
# Evento
# = = = = = = = = = = = = = =

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
            raise ParameterException("campo 'id' não definido")

    except ParameterException as ex:
        return { 'response': 'ERRO: ' + repr(ex) }, 400, headers 

    except Exception as ex:
        return { 'response': 'ERRO: Parametros mal configurados - ' + repr(ex) }, 400, headers 


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

        # TODO: investigar id
        # if(id is None):
        #     raise ParameterException("campo 'id' não definido")

    except ParameterException as ex:
        return { 'response': 'ERRO: ' + repr(ex) }, 400, headers 

    except Exception as ex:
        return { 'response': 'ERRO: Parametros mal configurados - ' + repr(ex) }, 400, headers 

    # executa funcao
    status, response = fn.buscarEventos(Database, texto, categoria, diaUpper, diaLower, horaUpper, horaLower, regiao)

    # responde
    if(status == 200):
        return { 'response': response }, 200, headers
    elif(status == 404):
        return { 'response': response }, 404, headers 
    else:
        return { 'response': response }, 500, headers
    

@app.route('/getBuscarParams', methods=['GET'])
def getBuscarParams():

    headers = getDefaultHeaders()

    result, response = fn.getBuscarParams(Database)

    if(result == 200):
        return { 'response': response }, 200, headers 
    else:
        return { 'response': response }, 500, headers 

# = = = = = = = = = = = = = =
# Usuario
# = = = = = = = = = = = = = =

@app.route('/usuario', methods=['POST', 'OPTIONS'])
def criarUsuario():
    
    headers = getDefaultHeaders()

    # preflight
    if request.method == 'OPTIONS':
        return {}, 204, headers

    # valida parametros
    try:            
        rdata = request.get_json()

        # TODO: aplicar esta estrutura no resto do arqiuvo
        email = str(rdata['email'])
        if email is None:
            raise ParameterException("campo 'email' não definido")
    
        nome = str(rdata['nome'])
        if nome is None:
            raise ParameterException("campo 'nome' não definido")
    
        senha = str(rdata['senha'])
        if senha is None:
            raise ParameterException("campo 'senha' não definido")
    
        tipo = str(rdata['tipo'])
        if tipo is None:
            raise ParameterException("campo 'tipo' não definido")
    
        cpf = str(rdata['cpf'])

    except ParameterException as ex:
        return { 'response': 'ERRO: ' + repr(ex) }, 400, headers 

    except Exception as ex:
        return { 'response': 'ERRO: Parametros mal configurados - ' + repr(ex) }, 400, headers 

    # executa funcao
    status, response = fn.criaUsuario(Database, email, nome, senha, tipo, cpf)

    # TODO: repensar isso aq???
    # responde
    if(status == 200):
        return { 'response': response }, 200, headers
    elif(status == 401):
        return { 'response': response }, 401, headers 
    elif(status == 404):
        return { 'response': response }, 404, headers 
    else:
        return { 'response': response }, 500, headers



if __name__ == "__main__":
    app.run(debug=True)