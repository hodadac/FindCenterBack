package com.gt.findingcenter.service.map;


import com.gt.findingcenter.dto.map.MapRequestDto;
import com.gt.findingcenter.dto.map.MapResponseDto;
import com.gt.findingcenter.entity.hull.Hull;
import com.gt.findingcenter.entity.map.Map;
import com.gt.findingcenter.repository.map.MapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.MathContext;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MapService {
    private final MapRepository mapRepository;

    public MapResponseDto findCenter(List<MapRequestDto> dtoList){
        int N = dtoList.size();
        ArrayList<Hull> list = new ArrayList<>(N);

        list.add(0,new Hull(BigDecimal.ZERO,BigDecimal.ZERO));
        for(int i=1; i<=N; i++){
            BigDecimal a = new BigDecimal(dtoList.get(i-1).getLat());
            BigDecimal b = new BigDecimal(dtoList.get(i-1).getLng());
            list.add(i, new Hull(a, b));
        }

        for(int i=1;i<=N;i++){
            int compareX = list.get(1).getX().compareTo(list.get(i).getX());
            int compareY = list.get(1).getY().compareTo(list.get(i).getY());

            if(compareY>0 || compareY==0 && compareX>0){
                Hull temp = list.get(1);
                list.set(1,list.get(i));
                list.set(i,temp);
            }
        }
        // ArraysList <> 배열 (list[])
        Hull hullList[] = list.toArray(new Hull[N]);
        // 위에서 배열로 바꾼 이유: Arrays.sort는 1번째 인수의 형태가 배열이어야 하는 것 같음
        // 좋은 의견있으면 변경 가능... 저는 못찾음
        Arrays.sort(hullList,2,N+1,new Comparator<Hull>(){
            @Override
            public int compare(Hull a, Hull b) {
                // TODO Auto-generated method stub
                int v = ccw(new Hull(hullList[1].getX(), hullList[1].getY()), a, b);
                System.out.println(v);
                // CCW 의 값 < 0 즉 음수일 때 b, c는 반시계 방향으로 존재 return 값은 1
                // 즉 최종 compare 에서의 return -1 현상 유지
                if (v > 0) return -1;

                // CCW 의 값 > 0 즉 양수일 때 b, c는 시계 방향으로 존재 return 값은 -1\
                // 즉 최종 compare 에서의 retrun 1 a와 b의 위치 변경 -> b , a
                if (v < 0) return 1;
                return (((a.getX().abs()).add(a.getY())).subtract((b.getX().abs()).add(b.getY()))).round(new MathContext(1)).intValue();
            }
        });

        //모든 위도 경로를 반시계 방향으로 정렬하고 그 중 최외각의 다각형을 구성하는 꼭짓점을 구하기
        Stack<Integer> stack = new Stack<>();
        stack.clear();
        stack.push(1);
        for(int i=2; i<=N; i++){

            while(stack.size() > 1 && ccw(hullList[stack.get(stack.size()-2)], hullList[stack.peek()], hullList[i]) <=0 ){
                stack.pop();
            }
            stack.add(i);
        }

        //구한 다각형의 무게 중심을 구하는 알고리즘
        BigDecimal totalX = new BigDecimal("0");
        BigDecimal totalY = new BigDecimal("0");
        BigDecimal num = new BigDecimal("3");
        for (int i=0; i<=stack.size()-1; i++){
            System.out.println("stack:value  = " + stack.get(i));
            System.out.println("list.x  = " + hullList[stack.get(i)].getX());
            System.out.println("list.y  = " + hullList[stack.get(i)].getY());
            totalX = totalX.add(hullList[stack.get(i)].getX());
            totalY = totalY.add(hullList[stack.get(i)].getY());
        }
        System.out.println("totalx = "+totalX);
        System.out.println("totaly = "+totalY);
        BigDecimal GX = totalX.divide(num,13, RoundingMode.DOWN);
        BigDecimal GY = totalY.divide(num,13,RoundingMode.DOWN);
        System.out.println(GX);
        System.out.println(GY);

        //추가 예정




        return  new MapResponseDto(GX.toString(),GY.toString());
    }



    protected static int ccw(Hull A, Hull B, Hull C) {

        BigDecimal left = (B.getX().subtract(A.getX())).multiply(C.getY().subtract(A.getY()));
        BigDecimal right = (C.getX().subtract(A.getX())).multiply((B.getY().subtract(A.getY())))  ;
        BigDecimal cal = left.subtract(right);
        int compareO = cal.compareTo(BigDecimal.ZERO);

        System.out.println("left = "+left);
        System.out.println("right = "+right);
        System.out.println("cal = "+cal);
        // CCW 의 값 < 0 즉 음수일 때 b, c는 반시계 방향으로 존재
        if(compareO < 0)    return 1;
            // CCW 의 값 > 0 즉 양수일 때 b, c는 시계 방향으로 존재
        else if (compareO > 0)    return -1;
        else    return 0;
    }



}
