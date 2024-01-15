describe("Test endpointu /signin", () => {
  it("Logowanie użytkownika", () => {
    const loginData = {
      email: "wlasciciel@gmail.com",
      password: "admin",
    };

    cy.request("POST", "http://localhost:8080/signin", loginData).then(
      (response) => {
        expect(response.status).to.eq(200);
        expect(response.body).to.have.property("token");
        expect(response.body).to.have.property("refreshToken");
        expect(response.body).to.have.property("email", loginData.email);
      }
    );
  });
});

describe("Test endpointu /addOrder", () => {
  it("Tworzenie zamówienia", () => {
    const url = "http://localhost:8080/addOrder";
    const params = {
      idPerson: 88,
      idProduct: 2,
      quantity: 3,
      token: "tok_visa",
    };

    cy.request({
      method: "POST",
      url: url,
      qs: params,
    }).then((response) => {
      expect(response.status).to.eq(200);
      expect(response.body).to.contain(
        "Zamówienie zostało pomyślnie utworzone."
      );
    });
  });
});

describe("Test endpointu /update-order-status", () => {
  it("Aktualizacja statusu zamówienia na 'Odebrane'", () => {
    const orderId = 10;
    const orderStatusDTO = {
      orderStatus: "Odebrane",
    };

    cy.request({
      method: "PUT",
      url: "http://localhost:8080/update-order-status",
      qs: { orderId: orderId },
      body: orderStatusDTO,
      failOnStatusCode: false,
    }).then((response) => {
      expect(response.status).to.eq(200);
    });
  });
});
