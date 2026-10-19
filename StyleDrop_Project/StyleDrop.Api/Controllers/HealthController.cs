using Microsoft.AspNetCore.Mvc;

namespace StyleDrop.Api.Controllers;

[ApiController]
[Route("api/health")]
public class HealthController : ControllerBase
{
    [HttpGet]
    public IActionResult Get() => Ok(new
    {
        status = "ok",
        service = "StyleDrop.Api",
        time = DateTime.UtcNow
    });
}
