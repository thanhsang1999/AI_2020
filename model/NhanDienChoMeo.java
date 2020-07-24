package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.datavec.image.loader.NativeImageLoader;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.VGG16ImagePreProcessor;
import org.slf4j.Logger;

public class NhanDienChoMeo {
	public String PATH_MODEL;
	public static ComputationGraph computationGraph;
	public NhanDienChoMeo() {
	}

	public PetType detectCat(File file) throws IOException {
		double threshold = 0.50;
		// kiểm tra xem mạng neural đã nạp dữ liệu chưa, nếu chưa thì nạp dữ liệu.
		if (computationGraph == null) {
			computationGraph = loadModel();
		}
		// khởi tạo mạng neural. Không tạo bản sao.
		computationGraph.init();
//        System.out.println(computationGraph.summary());
//		log.info(computationGraph.summary());
		// Khởi tạo matrix để lưu dữ liệu hình ảnh input
		NativeImageLoader loader = new NativeImageLoader(224, 224, 3);
		// Chuyển hình ảnh thành matrix
		INDArray image = loader.asMatrix(new FileInputStream(file));
		// Tiền xử lí cho VGG16
		DataNormalization scaler = new VGG16ImagePreProcessor();
		scaler.transform(image);
		// kiểm tra có hoặc mèo
		INDArray output = computationGraph.outputSingle(false, image);
		if (output.getDouble(0) > threshold) {
			return PetType.CAT;
		} else if (output.getDouble(1) > threshold) {
			return PetType.DOG;
		} else {
			return PetType.NO_KNOWN;
		}
	}
	public void setPathFile(String pathFile) {
		this.PATH_MODEL=pathFile;
	}
	public ComputationGraph loadModel() {
		try {
			if (PATH_MODEL!=null) {
				computationGraph = ModelSerializer.restoreComputationGraph(new File(PATH_MODEL));				
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return computationGraph;
	}
}

