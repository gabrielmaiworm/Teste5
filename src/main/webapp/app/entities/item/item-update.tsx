import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './item.reducer';
import { IItem } from 'app/shared/model/item.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { CategoriaItem } from 'app/shared/model/enumerations/categoria-item.model';
import { UnidadeMedida } from 'app/shared/model/enumerations/unidade-medida.model';

export const ItemUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const itemEntity = useAppSelector(state => state.item.entity);
  const loading = useAppSelector(state => state.item.loading);
  const updating = useAppSelector(state => state.item.updating);
  const updateSuccess = useAppSelector(state => state.item.updateSuccess);
  const categoriaItemValues = Object.keys(CategoriaItem);
  const unidadeMedidaValues = Object.keys(UnidadeMedida);
  const handleClose = () => {
    props.history.push('/item');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...itemEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          categoriaItem: 'ALIMENTO',
          unidadeMedida: 'CARTELA',
          ...itemEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="teste5App.item.home.createOrEditLabel" data-cy="ItemCreateUpdateHeading">
            Create or edit a Item
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="item-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Descricao" id="item-descricao" name="descricao" data-cy="descricao" type="text" />
              <ValidatedBlobField label="Imagem" id="item-imagem" name="imagem" data-cy="imagem" isImage accept="image/*" />
              <ValidatedField label="Quantidade" id="item-quantidade" name="quantidade" data-cy="quantidade" type="text" />
              <ValidatedField label="Categoria Item" id="item-categoriaItem" name="categoriaItem" data-cy="categoriaItem" type="select">
                {categoriaItemValues.map(categoriaItem => (
                  <option value={categoriaItem} key={categoriaItem}>
                    {categoriaItem}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label="Unidade Medida" id="item-unidadeMedida" name="unidadeMedida" data-cy="unidadeMedida" type="select">
                {unidadeMedidaValues.map(unidadeMedida => (
                  <option value={unidadeMedida} key={unidadeMedida}>
                    {unidadeMedida}
                  </option>
                ))}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/item" replace color="info">
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

export default ItemUpdate;
