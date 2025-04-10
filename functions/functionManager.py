from . import ping as fping
from . import evento as fevento
from . import buscarEventos as fbuscarev
from . import getBuscarParams as fbuscarparams


def ping():
    return fping.ping()


def evento(dbconf, id):
    return fevento.evento(dbconf, id)


def buscarEventos(dbconf, texto, categoria, diaUpper, diaLower, horaUpper, horaLower, regiao):
    return fbuscarev.buscarEventos(dbconf, texto, categoria, diaUpper, diaLower, horaUpper, horaLower, regiao)

def getBuscarParams(dbconf):
    return fbuscarparams.getBuscarParams(dbconf)