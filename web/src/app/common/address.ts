export class Address {

    street: string;
    city: string;
    state: string;
    country: string;
    zipCode: string;

    constructor(street: string,city: string,state: string,country: string,zipCode: string) {
        this.state = state;
        this.street = street;
        this.city = city;
        this.country = country;
        this.zipCode = zipCode;
    }
}
