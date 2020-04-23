import { Injectable } from '@angular/core';
import { Store } from '@ngrx/store';
import { Elevator } from '../models/elevator.model';
import { elevatorMoved } from '../state-management/evelator.actions';
import { ElevatorsState } from '../state-management/evelator.reducer';

@Injectable({
  providedIn: 'root'
})
export class ElevatorEventsService {

  private eventSource: EventSource;

  constructor(private store: Store<ElevatorsState>) { }

  public subscribeEventSource() {
    this.eventSource = new EventSource('/rest/v1/elevators/events');
    this.eventSource.addEventListener('ELEVATOR_MOVED', (event: MessageEvent) => {
      const elevator = JSON.parse(event.data) as Elevator;
      this.store.dispatch(elevatorMoved({ elevator }));
    });
    this.eventSource.addEventListener('error', (event: Event) => {
      if (this.eventSource.readyState !== EventSource.CONNECTING) {
        console.log('Server-Sent Event error: ', event);
      }
    });
  }
}
