from . import ping as fping
from . import evento as fevento


def ping():
    return fping.ping()


def evento(dbconf, id):
    return fevento.evento(dbconf, id)