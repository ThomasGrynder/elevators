import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { select, Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { Elevator } from '../../models/elevator.model';
import { ElevatorEventsService } from '../../services/elevator-events.service';
import { loadEvelators, requestElevatorSuccess, requestElevator } from '../../state-management/evelator.actions';
import { ElevatorsState } from '../../state-management/evelator.reducer';
import { selectElevators } from '../../state-management/evelator.selectors';

@Component({
  selector: 'elev-elevator-shaft',
  templateUrl: './elevator-shaft.component.html',
  styleUrls: ['./elevator-shaft.component.scss']
})
export class ElevatorShaftComponent implements OnInit {

  elevators$: Observable<Elevator[]>;

  form: FormGroup;

  constructor(private store: Store<ElevatorsState>, private eventsService: ElevatorEventsService, private fb: FormBuilder) {
    this.eventsService.subscribeEventSource();
  }

  ngOnInit() {
    this.elevators$ = this.store.pipe(select(selectElevators));
    this.store.dispatch(loadEvelators());
    this.form = this.fb.group({ floorNumber: [null, [Validators.required]] });
  }

  onSubmit() {
    this.store.dispatch(requestElevator({ floorNumber: this.form.value.floorNumber }));
  }
}
