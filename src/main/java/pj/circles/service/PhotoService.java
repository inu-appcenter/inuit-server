package pj.circles.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pj.circles.domain.Circle;
import pj.circles.domain.Photo;
import pj.circles.file.FileStore;
import pj.circles.repository.PhotoRepository;

import java.io.File;
import java.io.IOException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PhotoService {

    private final FileStore fileStore;
    @Value("${file.dir}")
    private String fileDir;
    @Autowired
    PhotoRepository photoRepository;
    @Transactional
    public Long join(MultipartFile multipartFile, Circle circle) throws IOException {
        Photo photo = fileStore.storeFile(multipartFile);
        photo.setCircle(circle);
        photoRepository.save(photo);
        return photo.getId();
    }
    @Transactional
    public void deletePhoto(Photo photo){
        File file = new File(fileDir+photo.getStoreFileName());

        if(file.exists()){

            file.delete();
            photoRepository.delete(photo);
        }

    }
    public String findPhoto(Long id){
        Photo photo = photoRepository.findById(id).get();
        return fileDir+photo.getStoreFileName();
    }

}
