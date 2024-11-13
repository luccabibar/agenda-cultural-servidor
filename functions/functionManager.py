from . import ping as fping
from . import evento as fevento


def ping():
    return fping.ping()


def evento(id):
    return fevento.evento(id)