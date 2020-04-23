import { createAction, props } from '@ngrx/store';
import { Elevator } from '../models/elevator.model';

export const evelatorFeatureKey = 'evelator';

export const loadEvelators = createAction(
  `[${evelatorFeatureKey}] Load Evelators`
);

export const loadEvelatorsSuccess = createAction(
  `[${evelatorFeatureKey}] Load Evelators Success`,
  props<{ elevators: Elevator[] }>()
);

export const loadEvelatorsFailure = createAction(
  `[${evelatorFeatureKey}] Load Evelators Failure`,
  (elevators = []) => ({ elevators })
);

export const elevatorMoved = createAction(
  `[${evelatorFeatureKey}] Evelator Moved`,
  props<{ elevator: Elevator }>()
);

export const requestElevator = createAction(
  `[${evelatorFeatureKey}] Request Evelator`,
  props<{ floorNumber: number }>()
);

export const requestElevatorSuccess = createAction(
  `[${evelatorFeatureKey}] Request Evelator Success`,
  props<{ elevator: Elevator }>()
);

export const requestElevatorFailure = createAction(
  `[${evelatorFeatureKey}] Request Evelator Failure`
);
