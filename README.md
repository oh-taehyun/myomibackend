# MyoMi
정기배송 구독 서비스  
### 프로젝트 소개  
### 서비스 이름: 묘미  
### 개발기간  
1차: 2023년 1월 3일~ 1월 24일  
2차: 2023년 1월 31일-2023년 3월 14일  

### 목적  
 1. spring data JPA 학습  
 스프링 Data JPA를 이용한 정기배송 쇼핑몰 서비스를 만들어 보면서 JPA 개발  
 구조를 익히고자 한다.
 
  2. 한 단계 발전한 정기배송 서비스  
  기존 정기배송은 샐러드, 도시락, 밀키트 등이 한번에 정기 주문할 수 있는 사이  
  트를 찾기 힘든데 묘미는 샐러드/도시락/밀키트를 주 단위로 한번에 주문할 수 있  
  게하여 배송비를 더 절감하고 포인트 적립의 기회를 더 가질 수 있도록 기획함.
    
  3. 웹 프로젝트 설계 과정 학습  
  프로그램을 구현하기 위해 DB설계, Backend, Frontend 모든 개발 과정에 직접  
  참여하고 Spring, Bootstrap 등을 이용해 전반적인 웹 사이트 설계 과정을 학  
  습.
  
  ### 개발환경  
   ● 개발 OS: Windows, MacOS  
   ● DBMS: Oracle 11g  
   ● Web: HTML5, CSS, JavaScript, jQUery, AJAX  
   ● 개발도구: Eclipse 4.26.0, VisualStudio 1.76.1  
   ● 웹서버: Apache Tomcat 9.0  
   ● 데이터베이스 툴 및 모델링 : SQL Developer, eXERD  
   ● 개발 언어 : Java11  
   ● 프레임워크: Spring FrameWork 5.3.25, Springboot 5.7.6, Spring Data  
    , JPA 5.7.6  
   ● 협업관리 툴: github  
   ● 프로젝트관리 툴: Notion  
   팀 구성: pm 1명, 팀원 4명  
     
   ### 요구사항  
   1.리뷰  
   -회원으로 로그인 시 마이페이지의 주문정보에서 내가 구매한 상품에 대한 리뷰를  
   작성할 수 있다.  
   -회원은 내 리뷰를 확인하고 수정할 수 있다.  
   2. 공지사항  
   -회원은 공지사항의 목록과 상세 글을 조회할 수 있다.  
   -관리자는 공지사항을 작성,수정,삭제할 수 있다.  
   3.관리자  
   -관리자는 일반회원의 판매자 신청 상태를 조회할 수 있다.  
   -관리자는 일반회원의 판매자 신청을 승인 혹은 반려해줄 수 있다.  
     
   ### 페이지별 설명  
   
   1. productinfo  
   ![](https://velog.velcdn.com/images/oh-taehyun/post/0de4baf5-db82-44f9-8fb1-6699c853bb35/image.png)

   기본설명  
   : 상품 상세보기를 통해 상품의 리뷰와 베스트 리뷰를 확인할 수 있다.
   
   마주친 문제  
   한 상품에 대해 베스트리뷰가 없어도 리뷰가 나오고 베스트리뷰가 있다면 리뷰와  
   베스트리뷰가 동시에 나오게 하려는데  
   쿼리문을 하나만 이용해서 상품에 따른 리뷰와 베스트리뷰를 찾으려니까 베스트리뷰  
   가 없으면 리뷰가 조회되지 않는 문제 발생.  
     
   해결: 리뷰를 찾는 쿼리문과 베스트 리뷰를 찾는 쿼리문을 따로 만들어주었다.  
   
   2.noticelist  
   ![](https://velog.velcdn.com/images/oh-taehyun/post/185a2a81-beee-4c91-b56c-7aa1120ca2d8/image.png)  
   
   기본설명: 로그인을 하지 않아도 공지를 조회하고 제목검색을 할 수 있다.  
   관리자로 로그인하지 않으면 글작성버튼이 보이지 않는다. 관리자로 로그인하면 글 작성 버튼을 눌러 작성페이지로 넘어갈 수 있다.  
     
   부딪혔던 난관: 공지사항 목록을 조회할 때 일반회원이 로그인하면 글작성 버튼이 보이지 않게 해야하는데 로그인 여부만으로는 일반회원과    관리자를 구분할 수 없었음.  
     
   해결 : 기본적으로 글 작성 버튼을 숨기고, 관리자id에게 role 번호 2를 부여해 role이 2일 때만 공지사항 목록에서 글작성버튼이 보이게             함.  
     
   3.noticedetail  
   ![](https://velog.velcdn.com/images/oh-taehyun/post/a12d0814-adde-4050-bcd4-1dcb6d46ff9f/image.png)  
     
   기본설명:  
   공지사항을 상세보기할 수 있다. 관리자로 로그인하지 않으면 수정버튼, 삭  
   제버튼이 보이지 않고, 관리자로 로그인 하면 위 두버튼이 보이며, 수정과 삭제를 할  
   수 있다.  
   공지 목록과 마찬가지로 관리자에게 user role 2를 부여해 로그인 시 쿠키로 가져온 
   user Role이 2일 때만 수정과 삭제버튼이 보이게 했다.  
     
   4. noticeadd  
   ![image](https://user-images.githubusercontent.com/66251647/231717424-2c001616-9016-456f-9707-8d549872e268.png)  
     
   기본설명:  
   공지 작성은 관리자만 가능하며, 제목, 내용을 입력하지 않으면 작성되  
   지 않는다. 내용은 최대 500자까지만 작성할 수 있으며 500자를 넘기면  
   알려준다.  
     
   5.notice-edit  
   ![image](https://user-images.githubusercontent.com/66251647/231717909-6b20c792-d6b3-419e-b7c5-c0cab62fefc7.png)  
     
   기본설명:  
   공지사항 수정은 제목과 내용을 수정할 수 있다. 내용은 500자까지만 작성 가능하다.  
     
   5. addreview  
   ![image](https://user-images.githubusercontent.com/66251647/231718328-78fe741e-4a2e-450f-92a3-b5112bb41abc.png)  
     
   기본설명:  
   마이페이지의 주문목록에서 리뷰를 작성할 수 있다. 리뷰는 제목, 별점, 내용, 사진 첨부를 할 수 있고, 사진  
   첨부를 제외하고 하나라도 작성되지 않으면 리뷰 작성이 완료되지 않는다.  
   별점은 1~5점 까지 0.5점 단위로 줄 수 있으며  최대 500자까지 작성가능하다. 사진을 첨부하지 않으면  일반리뷰로  
   등록되고, 사진을 첨부하면 포토리뷰로 등록되는데 일반리뷰는  
   200포인트를 받고, 포토리뷰는 500포인트를 받을 수 있다.  
   포토리뷰는 판매자에게 베스트리뷰로 선정될 수 있다.  
     
   부딪혔던 난관: 리뷰 작성시 상품번호와 주문번호 모두가 필요했는데, 내 주문페이지에서 받아오는 방법이 고민되었다.  
     
   해결: 주문번호랑 상품번호를 내 주문페이지에 숨겨놓고 리뷰작성을 눌렀을 때 주소에 쿼리스트링으로 받으면서 작성페이지로 넘어가게 했      다.
  
6. reviewmypage, reviewdetail  
![image](https://user-images.githubusercontent.com/66251647/231720272-26893cff-9092-4fa8-b00e-079535b49930.png)  
  
  ![image](https://user-images.githubusercontent.com/66251647/231720325-63359ee8-0533-45ab-bfff-08ad306178ce.png)  
  기본설명:  
  내 리뷰 목록에서 리뷰들을 볼 수 있다. 글을 클릭하면 상세보기를 할 수 있고, 베스트리뷰가 아니라면 수정도 가능하다.  
    
  7. reviewedit  
  ![image](https://user-images.githubusercontent.com/66251647/231720907-df2b935d-93bb-45f9-971f-fd08ff0a4e54.png)  
    
  기본설명:  
  리뷰 수정은 제목과 내용만 수정할 수 있다. 내용은 500자까지 수정 가능하다. 수정하는 리뷰의 상품명을 볼 수 있다.  
  만약 베스트리뷰라면 수정버튼을 눌렀을 때 수정버튼이 비활성화된다.
  
  8.sellerproductlist, sellerreviewlist  
  ![image](https://user-images.githubusercontent.com/66251647/231721549-8679b9a3-401d-4d15-88d0-5a8884e37ee2.png)  
  ![image](https://user-images.githubusercontent.com/66251647/231721646-117d1e00-25e4-4bda-a93a-1f73c9f18db9.png)  
    
   기본설명:  
   내 스토어 상품의 리뷰들을 볼 수 있으며 포토리뷰는 베스트리뷰 선정이 가능하다.  
   리뷰를 선정하고 나면 해당 리뷰의 리뷰선정 버튼이 비활성화 된다.  
     
   9.sellerinfolist, sellerdetail  
   ![image](https://user-images.githubusercontent.com/66251647/231722298-5de35595-09cb-49ca-ac19-4c68d070ca9e.png)  
   ![image](https://user-images.githubusercontent.com/66251647/231722363-5a37ecc4-e05e-41a0-9c48-c749601a1d5e.png)  
     
    기본설명:  
    관리자는 판매자 신청이 들어온 것을 관리자 페이지에서 승인상태별로 확인할 수 있다.  
    글을 눌렀을 때 승인 대기 혹은 승인 거절 상태라면 승인이나 승인거절을 눌러 상태변경을 할 수 있다.






   

   
   
   
   
