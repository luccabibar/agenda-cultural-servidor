from .common import databaseManager as dbm
from datetime import datetime as dt

def jaExiste(conn, email, nome, cpf):

    if(cpf == None):
        cpf = ''

    # monta request
    colnames = [
        'email', 'nome', 'cpf_org', 'cpf_mod'
    ]

    query = '''
        SELECT
            us.email, 
            us.nome, 
            og.cpf_cnpj, 
            md.cpf_cnpj 
        FROM usuario AS us
        FULL OUTER JOIN organizador AS og
            ON us.id = og.id
        FULL OUTER JOIN moderador AS md
            ON us.id = md.id
        WHERE
            UPPER(us.email) = UPPER(%s)
            OR UPPER(us.nome) = UPPER(%s)
            OR og.cpf_cnpj = %s
            OR md.cpf_cnpj = %s
    '''

    params = (email, nome, cpf, cpf)

    # executa
    result = dbm.select(conn, query, params, colnames)

    # retorna de acordo
    if(len(result) > 0):

        res = "Um usuário com estes dados já esta cadastrado"

        for rr in result :
            if(email.upper() == rr['email'].upper()):
                res = "Um usuário com este e-mail já está cadastrado"
                break
            elif(nome.upper() == rr['nome'].upper()):
                res = "Um usuário com este nome já está cadastrado"
                break
            elif(cpf == rr['cpf_org'] or cpf == rr['cpf_mod']):
                res = "Um usuário com este cpf já está cadastrado"
                break

        return True, res
    
    else:
        return False, "tudo certo :)"


def criaUsuario(dbconf, email, nome, senha, tipo, cpf = None):

    # conectar
    conn = dbm.connect(
        dbconf['Host'],
        dbconf['Port'],
        dbconf['Name'],
        dbconf['User'],
        dbconf['Password'],
    )

    if(conn is None):
        return 500, "impossivel conectar com o banco"

    # verifica se usuario ja existe
    jaEx, res = jaExiste(conn, email, nome, cpf)

    if(jaEx):
        return 401, res
    
    # TODO: seguir funcao
    else:
        return 200, res


    # segue com cadastro

    # senha deve ser texto + data

    # pos processamento
    result[0]['atualizacoes'] = []
    
    result[0]['organizador'] = { 'nome': result[0]['organizador'] } 

    if result[0]['attTexto'] is not None:
        
        for row in result:
            result[0]['atualizacoes'].append({
                'titulo': row['attTitulo'],
                'texto': row['attTexto']
            })

        del result[0]['attTitulo']
        del result[0]['attTexto']

    return 200, result[0]