import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, map, concatMap } from 'rxjs/operators';
import { EMPTY, of } from 'rxjs';

import * as EvelatorActions from './evelator.actions';



@Injectable()
export class EvelatorEffects {

  loadEvelators$ = createEffect(() => {
    return this.actions$.pipe( 

      ofType(EvelatorActions.loadEvelators),
      concatMap(() =>
        /** An EMPTY observable only emits completion. Replace with your own observable API request */
        EMPTY.pipe(
          map(data => EvelatorActions.loadEvelatorsSuccess({ data })),
          catchError(error => of(EvelatorActions.loadEvelatorsFailure({ error }))))
      )
    );
  });



  constructor(private actions$: Actions) {}

}
