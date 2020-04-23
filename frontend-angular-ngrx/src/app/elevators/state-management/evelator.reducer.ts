import { Action, createReducer, on } from '@ngrx/store';
import * as EvelatorActions from './evelator.actions';
import { Elevator } from '../models/elevator.model';

export interface ElevatorsState {
  elevators: Elevator[];  // TODO {[id: number]: Elevator}
  receivedElevatorId: number;
}

export const elevatorsInitialState: ElevatorsState = {
  elevators: [],
  receivedElevatorId: null
};

const evelatorReducer = createReducer(
  elevatorsInitialState,
  on(EvelatorActions.loadEvelatorsSuccess, EvelatorActions.loadEvelatorsFailure, (state, { elevators }) => ({
    ...state,
    elevators
  })),
  on(EvelatorActions.elevatorMoved, (state, { elevator }) => ({
    ...state,
    elevators: state.elevators.map(e => {
      if (e.id === elevator.id) {
        return elevator;
      }
      return e;
    })
  })),
  on(EvelatorActions.requestElevatorSuccess, (state, { elevator }) => ({
    ...state,
    receivedElevatorId: elevator.id
  })),
);

export function reducer(state: ElevatorsState | undefined, action: Action) {
  return evelatorReducer(state, action);
}
