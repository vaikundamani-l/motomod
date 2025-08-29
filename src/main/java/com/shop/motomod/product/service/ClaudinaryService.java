package com.shop.motomod.product.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClaudinaryService {

	private final Cloudinary cloudinary;

	
	// method to upload file to claudinary
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String, String> uploadFile(MultipartFile file, String folderName) throws IOException {
		Map<String, Object> options = ObjectUtils.asMap("folder", folderName);

		Map uploadResult = cloudinary.uploader().upload(file.getBytes(), options);

		String url = uploadResult.get("secure_url").toString();
		String publicId = uploadResult.get("public_id").toString();

		// return both values
		Map<String, String> result = new HashMap<>();
		result.put("url", url);
		result.put("public_id", publicId);
		return result;
	}
	
	
	// method to delete file from claudinary
	public String deleteFile(String publicId) throws IOException {
        Map<?, ?> result = cloudinary.uploader().destroy(
                publicId,
                ObjectUtils.emptyMap()
        );
        return result.get("result").toString(); // "ok" if deleted
    }

}
