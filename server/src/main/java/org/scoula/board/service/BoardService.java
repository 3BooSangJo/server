package .board.service;

import .board.domain.BoardAttachmentVO;
import .board.dto.BoardDTO;
import .common.pagination.Page;
import .common.pagination.PageRequest;

import java.util.List;

public interface BoardService {
    Page<BoardDTO> getPage(PageRequest pageRequest);

    public List<BoardDTO> getList();

    public BoardDTO get(Long no);

    public BoardDTO create(BoardDTO board);

    public BoardDTO update(BoardDTO board);

    public BoardDTO delete(Long no);

    public BoardAttachmentVO getAttachment(Long no);

    public boolean deleteAttachment(Long no);
}
