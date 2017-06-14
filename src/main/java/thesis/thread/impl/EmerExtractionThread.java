package thesis.thread.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import thesis.dto.BlockDto;
import thesis.exception.ServiceException;
import thesis.model.Block;

import java.util.List;

@Profile("emer")
@Component
public class EmerExtractionThread extends AbstractExtractionThread {

    @Override
    protected int extractCurrentBlockHeight() throws ServiceException {
        return rpcBlockRequestService.getCurrentBlockHeight();
    }

    @Override
    protected List<BlockDto> retrieveBlockHashes(Integer blockQuerySize, String blockhash, int blockHeight) throws ServiceException {
        return rpcBlockRequestService.getBlockHashesByHeight(blockHeight, blockHeight + blockQuerySize);
    }
}
