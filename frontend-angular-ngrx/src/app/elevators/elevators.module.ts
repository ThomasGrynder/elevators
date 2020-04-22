import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EffectsModule } from '@ngrx/effects';
import { StoreModule } from '@ngrx/store';
import { ElevatorShaftComponent } from './components/elevator-shaft/elevator-shaft.component';
import { ElevatorComponent } from './components/elevator/elevator.component';
import { EvelatorEffects } from './state-management/evelator.effects';
import * as fromEvelator from './state-management/evelator.reducer';

const routes: Routes = [
  { path: '', component: ElevatorShaftComponent }
];

@NgModule({
  declarations: [
    ElevatorShaftComponent,
    ElevatorComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    StoreModule.forFeature(fromEvelator.evelatorFeatureKey, fromEvelator.reducer),
    EffectsModule.forFeature([EvelatorEffects])
  ]
})
export class ElevatorsModule { }
