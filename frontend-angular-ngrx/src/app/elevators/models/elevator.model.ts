export type Direction = 'UP' | 'DOWN' | 'NONE';

export interface Elevator {
    id: number;
    addressedFloor: number;
    currentFloor: number;
    busy: boolean;
    direction: Direction;
}
