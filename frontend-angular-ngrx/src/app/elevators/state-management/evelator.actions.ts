import { createAction, props } from '@ngrx/store';

export const loadEvelators = createAction(
  '[Evelator] Load Evelators'
);

export const loadEvelatorsSuccess = createAction(
  '[Evelator] Load Evelators Success',
  props<{ data: any }>()
);

export const loadEvelatorsFailure = createAction(
  '[Evelator] Load Evelators Failure',
  props<{ error: any }>()
);
