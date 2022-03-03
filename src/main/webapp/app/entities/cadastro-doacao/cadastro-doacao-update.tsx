import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ILigacao } from 'app/shared/model/ligacao.model';
import { getEntities as getLigacaos } from 'app/entities/ligacao/ligacao.reducer';
import { IItem } from 'app/shared/model/item.model';
import { getEntities as getItems } from 'app/entities/item/item.reducer';
import { IAcao } from 'app/shared/model/acao.model';
import { getEntities as getAcaos } from 'app/entities/acao/acao.reducer';
import { getEntity, updateEntity, createEntity, reset } from './cadastro-doacao.reducer';
import { ICadastroDoacao } from 'app/shared/model/cadastro-doacao.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CadastroDoacaoUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const ligacaos = useAppSelector(state => state.ligacao.entities);
  const items = useAppSelector(state => state.item.entities);
  const acaos = useAppSelector(state => state.acao.entities);
  const cadastroDoacaoEntity = useAppSelector(state => state.cadastroDoacao.entity);
  const loading = useAppSelector(state => state.cadastroDoacao.loading);
  const updating = useAppSelector(state => state.cadastroDoacao.updating);
  const updateSuccess = useAppSelector(state => state.cadastroDoacao.updateSuccess);
  const handleClose = () => {
    props.history.push('/cadastro-doacao');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getLigacaos({}));
    dispatch(getItems({}));
    dispatch(getAcaos({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.dataDoacao = convertDateTimeToServer(values.dataDoacao);

    const entity = {
      ...cadastroDoacaoEntity,
      ...values,
      ligacao: ligacaos.find(it => it.id.toString() === values.ligacao.toString()),
      descricao: items.find(it => it.id.toString() === values.descricao.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          dataDoacao: displayDefaultDateTime(),
        }
      : {
          ...cadastroDoacaoEntity,
          dataDoacao: convertDateTimeFromServer(cadastroDoacaoEntity.dataDoacao),
          ligacao: cadastroDoacaoEntity?.ligacao?.id,
          descricao: cadastroDoacaoEntity?.descricao?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="teste5App.cadastroDoacao.home.createOrEditLabel" data-cy="CadastroDoacaoCreateUpdateHeading">
            Create or edit a CadastroDoacao
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="cadastro-doacao-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Doacao Anonima"
                id="cadastro-doacao-doacaoAnonima"
                name="doacaoAnonima"
                data-cy="doacaoAnonima"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Realiza Entrega"
                id="cadastro-doacao-realizaEntrega"
                name="realizaEntrega"
                data-cy="realizaEntrega"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Data Doacao"
                id="cadastro-doacao-dataDoacao"
                name="dataDoacao"
                data-cy="dataDoacao"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label="Logradouro" id="cadastro-doacao-logradouro" name="logradouro" data-cy="logradouro" type="text" />
              <ValidatedField label="Numero" id="cadastro-doacao-numero" name="numero" data-cy="numero" type="text" />
              <ValidatedField label="Bairro" id="cadastro-doacao-bairro" name="bairro" data-cy="bairro" type="text" />
              <ValidatedField label="Cidade" id="cadastro-doacao-cidade" name="cidade" data-cy="cidade" type="text" />
              <ValidatedField label="Cep" id="cadastro-doacao-cep" name="cep" data-cy="cep" type="text" />
              <ValidatedField label="Estado" id="cadastro-doacao-estado" name="estado" data-cy="estado" type="text" />
              <ValidatedField label="Pais" id="cadastro-doacao-pais" name="pais" data-cy="pais" type="text" />
              <ValidatedField label="Complemento" id="cadastro-doacao-complemento" name="complemento" data-cy="complemento" type="text" />
              <ValidatedField id="cadastro-doacao-ligacao" name="ligacao" data-cy="ligacao" label="Ligacao" type="select">
                <option value="" key="0" />
                {ligacaos
                  ? ligacaos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="cadastro-doacao-descricao" name="descricao" data-cy="descricao" label="Descricao" type="select">
                <option value="" key="0" />
                {items
                  ? items.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/cadastro-doacao" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default CadastroDoacaoUpdate;
