using Microsoft.AspNetCore.Http.HttpResults;
using Microsoft.AspNetCore.Mvc;

var builder = WebApplication.CreateBuilder(args);

List<Car> cars = new List<Car>();

cars.Add(new Car(1, "Toyota Corolla Garbage", "Toyota", "Corolla", 2000, "https://generatorfun.com/code/uploads/Random-Car-image-12221124.jpg", 2500, 100000, true));
cars.Add(new Car(2, "Honda Civic Type R", "Honda", "Civic", 2008, "https://generatorfun.com/code/uploads/Random-Car-image-19.jpg", 5000, 2000, true));
cars.Add(new Car(3, "BMW M5 CS 2022", "BMW", "M5", 2022, "https://generatorfun.com/code/uploads/Random-Car-image-12.jpg", 100000, 0, false));
cars.Add(new Car(4, "Ford Mustang BRMMM BRMM", "Ford", "Mustang", 1980, "https://generatorfun.com/code/uploads/Random-Car-image-17.jpg", 2500, 100000, true));
cars.Add(new Car(5, "Mercedes Benz C-Class", "Mercedes", "Model", 2020, "https://generatorfun.com/code/uploads/Random-Car-image-2.jpg", 50000, 0, false));
cars.Add(new Car(6, "Toyota Corolla Garbage", "Toyota", "Corolla", 2000, "https://generatorfun.com/code/uploads/Random-Car-image-12221124.jpg", 2500, 100000, true));
cars.Add(new Car(7, "Honda Civic Type R", "Honda", "Civic", 2008, "https://generatorfun.com/code/uploads/Random-Car-image-19.jpg", 5000, 2000, true));
cars.Add(new Car(8, "BMW M5 CS 2022", "BMW", "M5", 2022, "https://generatorfun.com/code/uploads/Random-Car-image-12.jpg", 100000, 0, false));
cars.Add(new Car(9, "Ford Mustang BRMMM BRMM", "Ford", "Mustang", 1980, "https://generatorfun.com/code/uploads/Random-Car-image-17.jpg", 2500, 100000, true));
cars.Add(new Car(10, "Mercedes Benz C-Class", "Mercedes", "Model", 2020, "https://generatorfun.com/code/uploads/Random-Car-image-2.jpg", 50000, 0, false));

// Add services to the container.

builder.Services.AddControllers();
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseHttpsRedirection();

app.UseAuthorization();

app.MapControllers();

app.MapGet(
    "/cars",
    () => cars);

app.MapGet(
    "/cars/{id:int}",
    (int id) => id >= 0 && id < cars.Count ? Results.Ok(cars[id]) : Results.NotFound());

app.Run();

public class Car
{
    public Car(int id, string name, string make, string model, int yearOfProduction, string imageUrl, int price, int mileage, bool used)
    {
        Id = id;
        Name = name;
        Make = make;
        Model = model;
        YearOfProduction = yearOfProduction;
        ImageUrl = imageUrl;
        Price = price;
        Mileage = mileage;
        Used = used;
    }
    
    public int Id { get; set; }
    public string Name { get; set; }
    public string Make { get; set; }
    public string Model { get; set; }
    public int YearOfProduction { get; set; }
    public string ImageUrl { get; set; }
    public int Price { get; set; }
    public int Mileage { get; set; }
    public bool Used { get; set; }
}