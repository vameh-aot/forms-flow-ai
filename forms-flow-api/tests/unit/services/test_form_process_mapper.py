"""Tests to assure the FormProcessMapper Service"""
from formsflow_api.services import FormProcessMapperService
from tests.utilities.base_test import get_form_service_payload

form_service = FormProcessMapperService()


def test_form_get_all_mappers(session):
    rv = form_service.get_all_mappers(page_number=0, limit=0)
    assert not rv


def test_get_form_mapper_count(session):
    rv = form_service.get_mapper_count()
    assert not rv
    assert type(rv) == int


def test_get_form_mapper(session):
    rv = form_service.get_mapper(1)
    assert not rv
    assert type(rv) == dict


def test_get_form_mapper_by_formid(session):
    rv = form_service.get_mapper_by_formid(1)
    assert not rv
    assert type(rv) == dict


def test_create_form_mapper(session):
    rv = form_service.create_mapper(data=get_form_service_payload())
    assert rv.form_id == "1234"
    assert rv.form_name == "Sample form"


def test_update_form_mapper(session, client):
    rv = form_service.update_mapper(1, data=get_form_service_payload())
    assert rv.form_id == "1234"
    assert rv.form_name == "Sample form"


def test_mark_inactive(session):
    rv = form_service.mark_inactive(form_process_mapper_id=1)
    assert rv.status_code == 200
