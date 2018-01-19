import { Apphour } from '../model/apphour';
import { User } from '../model/user';
import { Doctor } from '../model/doctor';
export interface Appointments {
    id: number;
    apphour: Apphour;
    date: string;
    doctor: Doctor;
    user: User;
}