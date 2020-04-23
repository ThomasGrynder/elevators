import { createFeatureSelector, createSelector } from '@ngrx/store';
import { evelatorFeatureKey } from './evelator.actions';
import * as fromEvelator from './evelator.reducer';

export const selectEvelatorState = createFeatureSelector<fromEvelator.ElevatorsState>(
  evelatorFeatureKey
);

export const selectElevators = createSelector(
  selectEvelatorState,
  (elevatorsState: fromEvelator.ElevatorsState) => elevatorsState.elevators
);
