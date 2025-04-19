from . import ping as fping
from . import evento as fevento
from . import buscarEventos as fbuscarev
from . import getBuscarParams as fbuscarparams
from . import usuario as fusuario


def ping():
    return fping.ping()

def evento(dbconf, id):
    return fevento.evento(dbconf, id)

def buscarEventos(dbconf, texto, categoria, diaUpper, diaLower, horaUpper, horaLower, regiao):
    return fbuscarev.buscarEventos(dbconf, texto, categoria, diaUpper, diaLower, horaUpper, horaLower, regiao)

def getBuscarParams(dbconf):
    return fbuscarparams.getBuscarParams(dbconf)

def criaUsuario(dbconf, email, nome, senha, tipo, cpf = None):
    return fusuario.criaUsuario(dbconf, email, nome, senha, tipo, cpf)