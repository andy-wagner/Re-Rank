import java.awt.List;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class MMR {
	private TermVectorSimilarity similarity=null;
	public MMR(){
		similarity=new TermVectorSimilarity();
	}
	/**
	 * ���㵱ǰ�ı����Ѿ�ѡ�����ı����ϵ����ƶ�
	 * @param rankedDocList �Ѿ�ѡ�����ı�����
	 * @param preparedDoc ��ǰ��ѡ����ı�
	 * @return ���ƶ�
	 * */
	private double getMaxValue(ArrayList<String> rankedDocList,String preparedDoc){
		double maxValue=0;
		for(String rankedDoc:rankedDocList){
			double tempValue=similarity.similar(rankedDoc, preparedDoc);
			if(tempValue>maxValue){
				maxValue=tempValue;
			}
		}
		return maxValue;
	}
	/**
	 * @param query ��ѯ��
	 * @param DocList �����������
	 * @return �������������
	 * */
	public ArrayList<String> rank(String query,ArrayList<String> docList){
		return rank(query,docList,docList.size());
	}
	/**
	 * @param query ��ѯ��
	 * @param DocList �����������
	 * @param cutOff ֮����ǰcouOff��
	 * @return �������������
	 * */
	public ArrayList<String> rank(String query,ArrayList<String> docList,int cutOff){
		ArrayList<String> rankedDocList=new ArrayList<String>();
		for(int i=0;i<cutOff;i++){
			double minValue=2;
			String minString=null;
			for(String doc:docList){
				//�������ı�����ѡ���ı����ϵ����ƶȡ��ı����ѯ�����ƶȡ��ı��ĳ����������档
				double tempValue=getMaxValue(rankedDocList, doc)-similarity.similar(doc, query)+(1/Math.pow(1.1,(doc.length())));
				if(tempValue<minValue){
					minValue=tempValue;
					minString=doc;
				}
			}
			docList.remove(minString);
			rankedDocList.add(minString);
		}
		return rankedDocList;
	}
}
