using CloudinaryDotNet.Actions;
using CloudinaryDotNet;


namespace prm392.Presenter.Business.SeatBusiness.CreateSeat;

public record CreateSeatCommand(Guid Id, string Name, string Type, short Capacity, short FloorNumber
    , IFormFile ImgUrl)
    : ICommand<CreateSeatResult>;
public record CreateSeatResult(Guid Id);
public class CreateSeatHandler(Prm392Context _db)
    : ICommandHandler<CreateSeatCommand, CreateSeatResult>
{
    private readonly Cloudinary _cloudinary;

    public async Task<CreateSeatResult> Handle(CreateSeatCommand request, CancellationToken cancellationToken)
    {
        var seat = await _db.Seats.FindAsync(request.Id);
        if (seat != null)
            throw new Exception("Seat already exist!");

        string? imageUrl = null;
        if (request.ImgUrl != null && request.ImgUrl.Length > 0)
        {
            var uploadParams = new ImageUploadParams
            {
                File = new FileDescription(request.ImgUrl.FileName, request.ImgUrl.OpenReadStream()),
                UseFilename = true,
                UniqueFilename = true,
                Overwrite = true
            };

            var uploadResult = await _cloudinary.UploadAsync(uploadParams);
            imageUrl = uploadResult.SecureUrl.ToString();
        }

        var seatNew = new Seat
        {
            Id = request.Id,
            Name = request.Name,
            Type = request.Type,
            Capacity = request.Capacity,
            Image = imageUrl,
            FloorNumber = request.FloorNumber
        };

        _db.Seats.Add(seatNew);
        await _db.SaveChangesAsync();
        return new CreateSeatResult(seatNew.Id);

    }
}
