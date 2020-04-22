import { Action, createReducer, on } from '@ngrx/store';
import * as EvelatorActions from './evelator.actions';

export const evelatorFeatureKey = 'evelator';

export interface State {

}

export const initialState: State = {

};

const evelatorReducer = createReducer(
  initialState,

  on(EvelatorActions.loadEvelators, state => state),
  on(EvelatorActions.loadEvelatorsSuccess, (state, action) => state),
  on(EvelatorActions.loadEvelatorsFailure, (state, action) => state),

);

export function reducer(state: State | undefined, action: Action) {
  return evelatorReducer(state, action);
}
