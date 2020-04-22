import { createFeatureSelector, createSelector } from '@ngrx/store';
import * as fromEvelator from './evelator.reducer';

export const selectEvelatorState = createFeatureSelector<fromEvelator.State>(
  fromEvelator.evelatorFeatureKey
);
