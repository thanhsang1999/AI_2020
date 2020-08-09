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
	public final Logger log;
	public final String TRAINED_PATH_MODEL;
	public static ComputationGraph computationGraph;
	public NhanDienChoMeo() {
		this.log = org.slf4j.LoggerFactory.getLogger(TrainImageNetVG16.class);
		TRAINED_PATH_MODEL = TrainImageNetVG16.DATA_PATH + "/model.zip";
	}
	
	public String Sang() {
		return "Nguyen Huy Thanh Sang";
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
		log.info(computationGraph.summary());
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

	public ComputationGraph loadModel() {
		try {
			computationGraph = ModelSerializer.restoreComputationGraph(new File("C:\\Project_2020\\Java\\AI_Project\\Project_AI_Web\\WebContent\\WEB-INF\\resources\\model.zip"));
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return computationGraph;
	}
}

