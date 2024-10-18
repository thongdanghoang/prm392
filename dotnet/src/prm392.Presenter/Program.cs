namespace prm392.Presenter;
public class Program
{
    public static void Main(string[] args)
    {
        var builder = WebApplication.CreateBuilder(args);

        // Add services to the container.
        builder.Services.AddAuthorization();
        builder.Services.AddDbContext<Prm392Context>(opts =>
        {
            opts.UseNpgsql(builder.Configuration.GetConnectionString("DefaultConnection"));
        });
        builder.Services.AddMediatR(opts =>
        {
            opts.RegisterServicesFromAssembly(typeof(Program).Assembly);
        });
        builder.Services.AddCarter();

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
        app.MapCarter();

        app.UseHttpsRedirection();

        app.UseAuthorization();

        app.Run();
    }
}
