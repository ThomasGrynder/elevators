import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { of } from 'rxjs';
import { catchError, map, mergeMap, switchMap } from 'rxjs/operators';
import { ElevatorService } from '../services/elevator.service';
import * as EvelatorActions from './evelator.actions';




@Injectable()
export class EvelatorEffects {

  loadEvelators$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(EvelatorActions.loadEvelators),
      switchMap(() =>
        this.elevatorService.fetchElevators().pipe(
          map(elevators => EvelatorActions.loadEvelatorsSuccess({ elevators })),
          catchError(error => of(EvelatorActions.loadEvelatorsFailure({ error }))))
      )
    );
  });

  requestElevator$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(EvelatorActions.requestElevator),
      mergeMap(payload =>
        this.elevatorService.requestElevator(payload.floorNumber).pipe(
          map(elevator => EvelatorActions.requestElevatorSuccess({ elevator })),
          catchError(() => of(EvelatorActions.requestElevatorFailure())))
      )
    );
  });

  constructor(private actions$: Actions, private elevatorService: ElevatorService) { }

}
