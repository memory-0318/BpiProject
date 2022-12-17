package tw.brian.hw.bpi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.brian.hw.bpi.dto.out.BpiResponseDTO;
import tw.brian.hw.bpi.service.BpiService;
import tw.brian.hw.general.model.ResponseDTO;

/**
 * @author Brian Su <memory0318@gmail.com>
 * @description:
 * @date: 2022/12/15
 */
@RestController
@RequestMapping(value = BpiController.BPI_REQUEST_PATH)
public class BpiController {
    public static final String BPI_REQUEST_PATH = "/current_bpi";

    @Autowired
    private BpiService bpiService;

    @GetMapping
    public ResponseDTO<BpiResponseDTO> getCurrentBpi() {
        return ResponseDTO.createSuccessResponse(
            this.bpiService.getCurrentBpi()
        );
    }
}
