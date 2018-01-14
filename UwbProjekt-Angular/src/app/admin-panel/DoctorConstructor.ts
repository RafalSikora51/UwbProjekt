export class Doctor {
    countryId: number;
    createdOn: any;
    email: string;
    lastName: string;
    firstName: string;
    admin: boolean;

    constructor(countryId: number, email: string, lastName: string, firstName: string, admin: boolean) {
        this.countryId = countryId;
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
        this.admin = admin;
    }
}